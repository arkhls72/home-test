package com.home.processor.job.service;

import java.util.concurrent.Callable;

public class CalculateService implements Callable<Integer>{
	
	private Integer value;
	public CalculateService(Integer value) {
		this.value = value;
	}

	
	@Override
	public Integer call() throws Exception {
		value = value * 2;
		return value;
	}
	
	
	
// 	HttpEntity<?> requestEntity = new HttpEntity<BalanceRequest>(entity,this.getJsonEntityHeader());
//             ResponseEntity<JsonElement> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,JsonElement.class);
            
//             ValidateUtil.responseEntity(responseEntity, "DP:Balances");

//             JsonElement jsonElement = (JsonElement)  responseEntity.getBody();
//             if (jsonElement ==null) {
//                 throw new AdapterException("Data power Balance response object [jsonElement] has no body.");
//             }

//             MlpErrorResponse source = gsonConverter.getGson().fromJson(responseEntity.getBody().toString(),MlpErrorResponse.class);
//             if (source == null) {
//                 throw new AdapterException("Data power Burn response object [MlpErrorResponse] has no body."); 
//             }
//              // statusCode would be the error received from DP. It is empty when there is no error 
//             if (StringUtils.isNotEmpty(source.getStatusCode())) {
//                  int errorStatus = Integer.valueOf(source.getStatusCode());
//                  AdapterStatus status = (errorStatus == AdapterStatus.NOT_FOUND.xmlValue()) ? AdapterStatus.NOT_FOUND:AdapterStatus.INTERNAL_SERVER_ERROR;

//                  this.setAdapterStatus(status);
//                  this.setErrorText(source.getErrorReason());

//                  return response;
//             }                 

//             BalanceResponse balance = gsonConverter.getGson().fromJson(responseEntity.getBody(), BalanceResponse.class);
            
//             PointsBalance target = BuilderProvider.build(balance);            
//             if (balance == null || target == null) {
//                 this.setAdapterStatus(AdapterStatus.NOT_FOUND);
//                 return response;
}
