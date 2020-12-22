import com.emilly.desafiobackend.exceptions.PatternFileException;
import com.emilly.desafiobackend.service.SystemDataService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SystemDataServiceTest {

    Map<String, List<String>> imaginaryData = new HashMap<>();

    @Before
    public void init() {
        List<String> imaginaryFile1 = new ArrayList<>();
        List<String> imaginaryFile2 = new ArrayList<>();
        List<String> imaginaryFile3 = new ArrayList<>();

        imaginaryFile1.add("001ç1234567891234çDiegoç50000 001ç3245678865434çRenatoç40000.99");
        imaginaryFile2.add("002ç2345675434544345çJose da SilvaçRural");
        imaginaryFile2.add("002ç2345675433444345çEduardoPereiraçRural");
        imaginaryFile3.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");
        imaginaryFile3.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");

        imaginaryData.put("File1", imaginaryFile1);
        imaginaryData.put("File2", imaginaryFile2);
        imaginaryData.put("File3", imaginaryFile3);

    }

    @Test
    public void populateCostumerList() throws PatternFileException {
        SystemDataService dataService = new SystemDataService();
        dataService.setAllData(imaginaryData);
        dataService.populateCostumerList();
        assertEquals(dataService.getCostumers().size(), 2);
    }

    @Test
    public void populateSalesmenList() throws PatternFileException {
        SystemDataService dataService = new SystemDataService();
        dataService.setAllData(imaginaryData);
        dataService.populateSalesmenList();
        assertEquals(dataService.getSalesmen().size(), 2);
    }

    @Test
    public void populateSalesList() throws PatternFileException {
        SystemDataService dataService = new SystemDataService();
        dataService.setAllData(imaginaryData);
        dataService.populateSalesList();
        assertEquals(dataService.getSaleList().size(), 2);
        assertEquals(dataService.getSaleList().get(0).getSales().size(), 3);
        assertEquals(dataService.getSaleList().get(1).getSales().size(), 3);

    }

    @Test
    public void getMostExpensiveSale() throws PatternFileException {
        SystemDataService dataService = new SystemDataService();
        dataService.setAllData(imaginaryData);
        dataService.populateSalesList();
        dataService.populateCostumerList();
        dataService.populateSalesmenList();
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        assertEquals(expected, dataService.returnMostExpensiveSale());
    }

    @Test
    public void getWorstSalesman() throws PatternFileException {
        SystemDataService dataService = new SystemDataService();
        dataService.setAllData(imaginaryData);
        dataService.populateSalesList();
        dataService.populateCostumerList();
        dataService.populateSalesmenList();
        assertEquals("Renato", dataService.worstSalesmanEver());


    }


}
