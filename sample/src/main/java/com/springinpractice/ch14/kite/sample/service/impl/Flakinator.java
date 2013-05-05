/*
 * Copyright (c) 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
