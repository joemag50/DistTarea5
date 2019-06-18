import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class Client
{
  public String cpu;
  public String ram;
  public String os;
  public String version;
  public String ip;
  public String ranking;
  public String cores;
  public String totalRam;
  public String ranking_static;
  
  public Client() {
    this.cpu = getCpu();
    this.ram = getAllocatedRam();
    this.os = getOS();
    this.version = getVersion();
    this.ip = getIp();
    this.ranking = "";
    this.cores = "" + Runtime.getRuntime().availableProcessors();
    this.totalRam = (new StringBuilder(String.valueOf(getTotalRam()))).toString().substring(0, 6);
    this.ranking_static = "";
  }
  
  public Client(String cpu, String ram, String os, String version, String ip, String ranking, String ranking_static) {
    this.cpu = cpu;
    this.ram = ram;
    this.os = os;
    this.version = version;
    this.ip = ip;
    this.ranking = ranking;
    this.cores = "" + Runtime.getRuntime().availableProcessors();
    this.totalRam = humanReadableByteCount(getTotalRam(), true);
    this.ranking_static = ranking_static;
  }

  
  public String labelText() { return String.format("%s - %s - %s - %s - %s - 3 Mbps - %s - %s", new Object[] { this.ip, this.os, this.version, this.cpu, this.ram, this.ranking_static, this.ranking }); }


  
  public double getRam() { return Double.parseDouble(this.ram.substring(0, this.ram.length() - 3)); }


  
  public double getCpuDouble() { return Double.parseDouble(this.cpu.substring(0, this.cpu.length() - 3)); }



  
  public String getOS() { return System.getProperty("os.name").toLowerCase(); }



  
  public String getVersion() { return System.getProperty("os.version"); }


  
  public long getTotalRam() {
    long ram = 0L;
    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean(); byte b; int i; Method[] arrayOfMethod;
    for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
      method.setAccessible(true);
      if (method.getName().startsWith("getTotalPhysicalMemorySize") && Modifier.isPublic(method.getModifiers())) {
        Object value;
        try {
          value = method.invoke(operatingSystemMXBean, new Object[0]);
        } catch (Exception e) {
          value = e;
        } 
        ram = ((Long)value).longValue();
      }
    }
    return ram;
  }

  
  public long getFreeRam() {
    long ram = 0L;
    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean(); byte b; int i; Method[] arrayOfMethod;
    for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
      method.setAccessible(true);
      if (method.getName().startsWith("getFreePhysicalMemorySize") && Modifier.isPublic(method.getModifiers())) {
        Object value;
        try {
          value = method.invoke(operatingSystemMXBean, new Object[0]);
        } catch (Exception e) {
          value = e;
        } 
        ram = ((Long)value).longValue();
      }
    }
    return ram;
  }


  
  public String getAllocatedRam() { return humanReadableByteCount(getTotalRam() - getFreeRam(), true); }


  
  public String getCpu() {
    Double cpu_load = Double.valueOf(0.0D);
    OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean(); byte b; int i; Method[] arrayOfMethod;
    for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
      method.setAccessible(true);
      if (method.getName().startsWith("getSystemCpuLoad") && Modifier.isPublic(method.getModifiers())) {
        Object value;
        try {
          value = method.invoke(operatingSystemMXBean, new Object[0]);
        } catch (Exception e) {
          value = e;
        } 
        cpu_load = (Double)value;
      }
    }
    
    return String.valueOf(String.format("%.3f", new Object[] { Double.valueOf(cpu_load.doubleValue() * 100.0D) })) + "%";
  }

  
  public String getIp() {
    String ip = "";
    
    try {
      Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
      for (NetworkInterface netint : Collections.list(nets)) {
        
        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
          String inetadd = inetAddress + "";
          inetadd = inetadd.substring(1);
          if (inetadd.startsWith("172")) {
            ip = inetadd;
          }
        } 
      } 
    } catch (SocketException e) {
      e.printStackTrace();
    } 
    return ip;
  }
  
  public String humanReadableByteCount(long bytes, boolean si) {
    int unit = si ? 1000 : 1024;
    if (bytes < unit) return String.valueOf(bytes) + " B"; 
    int exp = (int)(Math.log(bytes) / Math.log(unit));
    String pre = String.valueOf((si ? "kMGTPE" : "KMGTPE").charAt(exp - 1)) + (si ? "" : "i");
    return String.format("%.1f %sB", new Object[] { Double.valueOf(bytes / Math.pow(unit, exp)), pre });
  }
}
