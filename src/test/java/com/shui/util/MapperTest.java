package com.shui.util;

import java.awt.Button;

import org.testng.annotations.Test;

import com.shui.util.common.Mapper;

public class MapperTest {
	@Test
	public void objMapperTest() {
		Mapper mapper = new Mapper();
		Car car = new Car();
		Button button = mapper.map(car, Button.class);
		System.out.println("button's label is:" + button.getLabel());
	}
}

class Car {
	String name = "Car";
	String label = "no label";
	int defaultCenturyStart = 47;

	public String getName() {
		return this.name;
	}
}