package org.effortless.tests.examples.mvvm;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import org.effortless.tests.examples.mvvm.Car;
import org.effortless.tests.examples.mvvm.CarService;
import org.effortless.tests.examples.mvvm.CarServiceImpl;

public class SearchViewModel {
	
	private String keyword;
	private List<Car> carList;
	private Car selectedCar;
	
	private CarService carService = new CarServiceImpl();
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getKeyword() {
		return keyword;
	}

	public List<Car> getCarList(){
		return carList;
	}
	
		
	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}
	public Car getSelectedCar() {
		return selectedCar;
	}

	
	@Command
	@NotifyChange("carList")
	public void search(){
		carList = carService.search(keyword);
	}
}
