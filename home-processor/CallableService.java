package com.home.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.home.manager.ManufacturerManager;
@Service
public class CallableService {

    public ManufacturerCallable manufacturer(String keyword, List<Integer> ids, MyPageSize pageSize) {
        return new ManufacturerCallable(keyword, ids, pageSize);
    }

   

    private class ManufacturerCallable implements Callable<List<MyDTO>> {
        private List<Integer> ids;
        private String keyword;
        private MyPageSize pageSize;

        private ManufacturerCallable(String keyword, List<Integer> ids, MyPageSize pageSize) {
            this.keyword = keyword;
            this.ids = ids;
            this.pageSize = pageSize;
        }

        @Override
        public List<MyDTO> call() throws Exception {
            List<MyDTO> target = new ArrayList<>();

            List<Dataset> response = manufacturerManager.retrieveScopeSearch(ids, keyword, pageSize);

            if (CollectionUtils.isNotEmpty(response)) {
                AppendHelper.convert(response, target);
            }

            return target;
        }
    }

            Callable<List<MyDTO>> manufacturerCallable = callableService.manufacturer(keyword, ids, pageSize);
            Future<List<MYDTO>> manufacturers = taskExecutor.submit(manufacturerCallable);

            target.addAll(manufacturers.get());
    

}
