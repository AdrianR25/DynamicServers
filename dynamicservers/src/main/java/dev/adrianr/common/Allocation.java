package dev.adrianr.common;

public class Allocation {

	String ip;
	int port, id;
	boolean assigned;
	
	public Allocation(String ip, int port, boolean assigned, int id) {
		this.ip = ip;
		this.port = port;
		this.assigned = assigned;
		this.id = id;
	}

	public Allocation(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Allocation [assigned=" + assigned + ", ip=" + ip + ", port=" + port + "]";
	}

	@Override
	public boolean equals(Object obj) {
		
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Allocation)) {
            return false;
        }

		Allocation a = (Allocation) obj;
		if (a.getId() == this.getId()) return true;

		return a.getPort() == this.getPort() && a.getIp().equals(this.getIp());

	}

	

}
