
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

}
