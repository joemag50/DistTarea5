import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Client {
	public String cpu;
	public String ram;
	public String os;
	public String version;

	public Client(String cpu, String ram, String os, String version) {
		this.cpu = cpu;
		this.ram = ram;
		this.os = os;
		this.version = version;
	}

	public String labelText() {
		return String.format("%s %s %s %s", cpu, ram, os, version);
	}

	public double getRam() {
		return Double.parseDouble(ram.substring(0, ram.length() - 3));
	}

	public double getCpu() {
		return Double.parseDouble(cpu.substring(0, cpu.length() - 3));
	}

    public String getOS()
	{
		return System.getProperty("os.name").toLowerCase();
	}

	public String getVersion()
	{
		return System.getProperty("os.version");
	}
	public long getTotalRam()
	{
		long ram = 0;
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("getTotalPhysicalMemorySize") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				ram = (long) value;
				System.out.println(method.getName() + " = " + value);
			} // if
		}// for
		return ram;
	}
	public long getFreeRam()
	{
		long ram = 0;
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("getFreePhysicalMemorySize") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				ram = (long) value;
				System.out.println(method.getName() + " = " + value);
			} // if
		}// for
		return ram;
	}
	public String getAllocatedRam()
	{
		return this.humanReadableByteCount(getTotalRam() - getFreeRam(), true);
	}
	public String getCpu()
	{
		Double cpu_load = 0.0;
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("getSystemCpuLoad") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				cpu_load = (Double) value;
				System.out.println(method.getName() + " = " + value);
			} // if
		}// for
		String result = String.format("%.3f", (cpu_load * 100)) + "%";
		return result;
	}

	private static void printUsage() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get") && Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				System.out.println(method.getName() + " = " + value);
			} // if
		} // for
	}

	public String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
