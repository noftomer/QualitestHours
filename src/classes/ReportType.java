package classes;

public class ReportType {
	private String day;
	private String entryTime;
	private String exitTime;
	private String timePerDay;
	public ReportType(String day,String entryTime,String exitTime,String timePerDay)
	{
		this.day=day;
		this.entryTime=entryTime;
		this.exitTime=exitTime;
		this.timePerDay=timePerDay;
	}
	public String getDay() {
		return day;
	}
	public String getEntryTime() {
		return entryTime;
	}
	public String getExitTime() {
		return exitTime;
	}
	public String getTimePerDay() {
		return timePerDay;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "day="+getDay()+", enterHour="+getEntryTime()+", exitHour="+getExitTime()+", sum="+getTimePerDay();
	}
	
	
}
