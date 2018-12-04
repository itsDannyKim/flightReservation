package UserTypes;

public class Flight {

	private int FlightId;
	private String carrier;
	private String DepartingDate;
	private String ArrivalDate;
	private String DepartingCity;
	private String ArrivingCity;
	private String ArrivalTime;
	private String DepartingTime;
	private int currentPassengers;
	private int PassengerLimit;
	private int Price;
	private boolean isFull;
	
	public Flight(int FlightId, String Carrier, String DepartingCity, String ArrivingCity, String DepartingTime,
			String ArrivalTime, String DepartingDate, String ArrivalDate, int currentPassengers, int PassengerLimit, int Price) {

		this.FlightId = FlightId;
		this.carrier = Carrier;
		this.DepartingDate = DepartingDate;
		this.ArrivalDate = ArrivalDate;
		this.DepartingCity = DepartingCity;
		this.ArrivingCity = ArrivingCity;
		this.DepartingTime = DepartingTime;
		this.ArrivalTime = ArrivalTime;
		this.currentPassengers = currentPassengers;
		this.PassengerLimit = PassengerLimit;
		this.Price = Price;
		
	}

	public int getFlightId() {

		return FlightId;

	}

	public void setFlightId(int flightId) {

		this.FlightId = flightId;

	}

	public String getDepartingDate() {

		return DepartingDate;

	}

	public void setDepartingDate(String DepartingDate) {

		this.DepartingDate = DepartingDate;

	}

	public String getArrivalDate() {

		return ArrivalDate;

	}

	public void setArrivalDate(String ArrivalDate) {

		this.ArrivalDate = ArrivalDate;

	}

	public String getDepartingCity() {

		return DepartingCity;

	}

	public void setDepartingCity(String DepartingCity) {

		this.DepartingCity = DepartingCity;

	}

	public String getArrivingCity() {

		return ArrivingCity;

	}

	public void setArrivingCity(String ArrivingCity) {

		this.ArrivingCity = ArrivingCity;

	}

	public int getCurrentPassengers() {

		return currentPassengers;

	}

	public void setCurrentPassengers(int currentPassengers) {

		this.currentPassengers = currentPassengers;

	}

	public boolean isFull() {

		return isFull;

	}

	public void setFull(boolean isFull) {

		this.isFull = isFull;

	}

	public String getDepartingTime() {
		return DepartingTime;
	}

	public void setDepartingTime(String departureTime) {
		this.DepartingTime = departureTime;
	}

	public String getArrivalTime() {
		return ArrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.ArrivalTime = arrivalTime;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getPassengerLimit() {
		return PassengerLimit;
	}

	public void setPassengerLimit(int passengerLimit) {
		PassengerLimit = passengerLimit;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

}