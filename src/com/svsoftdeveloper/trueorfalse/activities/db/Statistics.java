package com.svsoftdeveloper.trueorfalse.activities.db;

public class Statistics {
	
	private int id;
	
	private float l1Percents;
	private float l2Percents;
	private float l3Percents;
	private float l4Percents;
	private float l5Percents;
	
	private int l1Done;
	private int l2Done;
	private int l3Done;
	private int l4Done;
	private int l5Done;
	
	public Statistics(int id, float l1Percents, float l2Percents,
			float l3Percents, float l4Percents, float l5Percents, int l1Done,
			int l2Done, int l3Done, int l4Done, int l5Done) {
		super();
		this.id = id;
		this.l1Percents = l1Percents;
		this.l2Percents = l2Percents;
		this.l3Percents = l3Percents;
		this.l4Percents = l4Percents;
		this.l5Percents = l5Percents;
		this.l1Done = l1Done;
		this.l2Done = l2Done;
		this.l3Done = l3Done;
		this.l4Done = l4Done;
		this.l5Done = l5Done;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getL1Percents() {
		return l1Percents;
	}

	public void setL1Percents(float l1Percents) {
		this.l1Percents = l1Percents;
	}

	public float getL2Percents() {
		return l2Percents;
	}

	public void setL2Percents(float l2Percents) {
		this.l2Percents = l2Percents;
	}

	public float getL3Percents() {
		return l3Percents;
	}

	public void setL3Percents(float l3Percents) {
		this.l3Percents = l3Percents;
	}

	public float getL4Percents() {
		return l4Percents;
	}

	public void setL4Percents(float l4Percents) {
		this.l4Percents = l4Percents;
	}

	public float getL5Percents() {
		return l5Percents;
	}

	public void setL5Percents(float l5Percents) {
		this.l5Percents = l5Percents;
	}

	public int getL1Done() {
		return l1Done;
	}

	public void setL1Done(int l1Done) {
		this.l1Done = l1Done;
	}

	public int getL2Done() {
		return l2Done;
	}

	public void setL2Done(int l2Done) {
		this.l2Done = l2Done;
	}

	public int getL3Done() {
		return l3Done;
	}

	public void setL3Done(int l3Done) {
		this.l3Done = l3Done;
	}

	public int getL4Done() {
		return l4Done;
	}

	public void setL4Done(int l4Done) {
		this.l4Done = l4Done;
	}

	public int getL5Done() {
		return l5Done;
	}

	public void setL5Done(int l5Done) {
		this.l5Done = l5Done;
	}
	
}
