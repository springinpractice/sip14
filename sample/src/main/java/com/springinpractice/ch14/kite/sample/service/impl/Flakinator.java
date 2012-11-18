package com.springinpractice.ch14.kite.sample.service.impl;

import org.springframework.stereotype.Component;

/**
 * @author Willie Wheeler (willie.wheeler@gmail.com)
 */
@Component
public class Flakinator {
	private volatile boolean up = true;
		
	public void simulateFlakiness() {
		if (up) {
			if (Math.random() < 0.05) {
				this.up = false;
			}
		} else {
			if (Math.random() < 0.2) {
				this.up = true;
			}
		}
		
		if (!up) {
			throw new RuntimeException("Oops, service down");
		}
	}

}
