package com.ace.example;

import com.ace.example.chapter01.Duck;
import com.ace.example.chapter01.MallardDuck;
import com.ace.example.chapter01.ModelDuck;
import com.ace.example.chapter01.fly.FlyRocketPowered;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleApplication {

	public static void main(String[] args) {
		Duck mallard = new MallardDuck();
		mallard.performQuack();
		mallard.performFly();

		Duck model = new ModelDuck();
		model.performFly();
		model.setFlyBehavior(new FlyRocketPowered());
		model.performFly();
	}

}
