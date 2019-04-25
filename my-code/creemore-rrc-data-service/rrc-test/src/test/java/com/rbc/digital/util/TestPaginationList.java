package com.home.digital.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import com.home.digital.common.util.Paginatore;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:rest-client-context.xml" })

/**
 *  unit  for Paginator
 * @author arash
 *
 */
public class TestPaginationList {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Test
	public void testPagination() throws Exception {
        testStringList();
        testIntegerList();
    }
    
    private void testStringList() throws Exception {
        List<String> persons = new ArrayList<String>();
        persons.add("A");
        persons.add("B");
        
        persons.add("C");
        persons.add("D");
        
        persons.add("E");
        persons.add("F");
        persons.add("G");
        
        persons.add("H");
        persons.add("I");
        persons.add("G");
        persons.add("K");
        persons.add("L");
        persons.add("M");
        persons.add("N");
        persons.add("O");
        persons.add("P");
        persons.add("Q");

        int pageSize = 7;

        Paginatore<String> pageinator = new Paginatore<String>(persons,pageSize);
        logger.info("Default page size:[{}]",pageinator.getDefaultPageSize());
        logger.info("Page size:[{}]",pageinator.getPageSize());
        logger.info("Last page couont:[{}]",pageinator.getLastPage());
        logger.info("----------+------------");
        
        int desirePage = 2;
        
        logger.info("Display page:[{}]",desirePage);
        logger.info("----------+------------");

        StopWatch watch = new StopWatch();
        watch.start();
        
        List<String> pageList = pageinator.getListByPageNumber(desirePage);

        watch.stop();
        logger.info("Time millisecond:[{}]",watch.getTotalTimeMillis());
        
        logger.info("Default page size:[{}]",pageinator.getDefaultPageSize());
        logger.info("Page size:[{}]",pageinator.getPageSize());
        logger.info("Last page couont:[{}]",pageinator.getLastPage());
        logger.info("----------+------------");

        StringBuilder sb = new StringBuilder();
        int i=1;
        sb.append("{");
        for (String item:pageList) {
            sb.append(item);
            if (i<pageList.size()) {
                sb.append(",");    
            }
            i ++;
        }
        sb.append("}");

        logger.info("List = [{}]",sb.toString());
    }
    
    private void testIntegerList() throws Exception  {
        List <Integer> list = new ArrayList<Integer>();
        
        for (int i=1;i<2000;i++) {
            list.add(i);
        }

        int pageSize = 0;

        Paginatore<Integer> pageinator = new Paginatore<Integer>(list,pageSize);
        logger.info("Default page size:[{}]",pageinator.getDefaultPageSize());
        logger.info("Page size:[{}]",pageinator.getPageSize());
        logger.info("Last page couont:[{}]",pageinator.getLastPage());
        logger.info("----------+------------");

        int desirePage = 800;
        
        logger.info("Display page:[{}]",desirePage);
        logger.info("----------+------------");

        StopWatch watch = new StopWatch();
        watch.start();
        
        List<Integer> pageList = pageinator.getListByPageNumber(desirePage);

        watch.stop();
        logger.info("Time millisecond:[{}]",watch.getTotalTimeMillis());
        
        StringBuilder sb = new StringBuilder();
        int i=1;
        sb.append("{");
        for (Integer item:pageList) {
            sb.append(item);
            if (i<pageList.size()) {
                sb.append(",");    
            }
            i ++;
        }
        sb.append("}");

        logger.info("List = [{}]",sb.toString());
    }
}
