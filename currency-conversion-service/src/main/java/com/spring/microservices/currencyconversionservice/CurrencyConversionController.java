package com.spring.microservices.currencyconversionservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(("currency-converter/from/{from}/to/{to}/quantity/{quantity}"))
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean currencyConversionBean = currencyExchangeServiceProxy.getExchangeRate(from, to);
		logger.info("currencyConversionBean {}",currencyConversionBean);
		
		return new CurrencyConversionBean(currencyConversionBean.getId(), from, to,
				currencyConversionBean.getConversionMultiple(), quantity,
				quantity.multiply(currencyConversionBean.getConversionMultiple()), currencyConversionBean.getPort());
	}

}
