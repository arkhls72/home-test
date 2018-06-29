import java.util.ArrayList;
import java.util.List;


public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        Long rows =  5000L;
        Long SIZE = 500L;
        long page=0L;
        
        double devide = rows/SIZE;
        
        page = rows % SIZE == 0 ? (long) devide:(long) devide + 1;
         
//        Long index = 0L;
//        List<EventDetails> eventDetails = new ArrayList<>();
//        
//        for (int i=1;i<=page;i++) {
//            EventDetails event = new EventDetails();
//   
//            event.setStart( 1 + index); 
//            index = SIZE * i;
//            event.setEnd(Long.valueOf(index));
//            
//            
//            event.setName("page-" + String.valueOf(i));
//            eventDetails.add(event);
//        } 
//        System.out.println(eventDetails);
        }
        

    

}
