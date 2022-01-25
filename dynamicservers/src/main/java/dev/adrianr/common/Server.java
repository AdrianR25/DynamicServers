package dev.adrianr.common;

public class Server {

	private int ownerId, nodeId, allocationLimit, databaseLimit, backupLimit, memory,
			swap, diskSpace, blockIoWeight, cpuLimit, eggId;
	private String name, dockerImage, cpuPinning, startupCommand;
	private boolean enableOom;

	public Server() {
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getBackupLimit() {
		return backupLimit;
	}

	public void setBackupLimit(int backupLimit) {
		this.backupLimit = backupLimit;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getSwap() {
		return swap;
	}

	public void setSwap(int swap) {
		this.swap = swap;
	}

	public int getDiskSpace() {
		return diskSpace;
	}

	public void setDiskSpace(int diskSpace) {
		this.diskSpace = diskSpace;
	}

	public int getBlockIoWeight() {
		return blockIoWeight;
	}

	public void setBlockIoWeight(int blockIoWeight) {
		this.blockIoWeight = blockIoWeight;
	}

	public int getCpuLimit() {
		return cpuLimit;
	}

	public void setCpuLimit(int cpuLimit) {
		this.cpuLimit = cpuLimit;
	}

	public int getEggId() {
		return eggId;
	}

	public void setEggId(int eggId) {
		this.eggId = eggId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDockerImage() {
		return dockerImage;
	}

	public void setDockerImage(String dockerImage) {
		this.dockerImage = dockerImage;
	}

	public String getCpuPinning() {
		return cpuPinning;
	}

	public void setCpuPinning(String cpuPinning) {
		this.cpuPinning = cpuPinning;
	}

	public boolean isEnableOom() {
		return enableOom;
	}

	public void setEnableOom(boolean enableOom) {
		this.enableOom = enableOom;
	}

	public int getAllocationLimit() {
		return allocationLimit;
	}

	public void setAllocationLimit(int allocationLimit) {
		this.allocationLimit = allocationLimit;
	}

	public int getDatabaseLimit() {
		return databaseLimit;
	}

	public void setDatabaseLimit(int databaseLimit) {
		this.databaseLimit = databaseLimit;
	}

	public String getStartupCommand() {
		return startupCommand;
	}

	public void setStartupCommand(String startupCommand) {
		this.startupCommand = startupCommand;
	}

	@Override
	public String toString() {
		return "Server [allocationLimit=" + allocationLimit + ", backupLimit=" + backupLimit + ", blockIoWeight="
				+ blockIoWeight + ", cpuLimit=" + cpuLimit + ", cpuPinning=" + cpuPinning + ", databaseLimit="
				+ databaseLimit + ", diskSpace=" + diskSpace + ", dockerImage=" + dockerImage + ", eggId=" + eggId
				+ ", enableOom=" + enableOom + ", memory=" + memory + ", name=" + name + ", nodeId=" + nodeId
				+ ", ownerId=" + ownerId + ", swap=" + swap + "]";
	}	

}
