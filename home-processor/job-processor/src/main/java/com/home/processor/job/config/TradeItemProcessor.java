package com.home.processor.job.config;

import org.springframework.batch.item.ItemProcessor;

import com.home.processor.job.dto.TradeSupplierDTO;
import com.home.processor.job.model.TradeSupplier;

public class TradeItemProcessor implements ItemProcessor<TradeSupplierDTO, TradeSupplier>{

	@Override
	public TradeSupplier process(TradeSupplierDTO arg0) throws Exception {
		return new TradeSupplier();
	}
}
