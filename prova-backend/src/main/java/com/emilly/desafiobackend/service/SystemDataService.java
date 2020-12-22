package com.emilly.desafiobackend.service;

import com.emilly.desafiobackend.dao.SystemDataDao;
import com.emilly.desafiobackend.exceptions.PatternFileException;
import com.emilly.desafiobackend.model.Costumer;
import com.emilly.desafiobackend.model.Sale;
import com.emilly.desafiobackend.model.SaleDetails;
import com.emilly.desafiobackend.model.Salesman;

import java.nio.file.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class SystemDataService {

    private SystemDataDao systemDataDao;
    private Map<String, List<String>> allData;
    private List<Costumer> costumers;
    private List<Sale> saleList;
    private List<Salesman> salesmen;

    private String salesmanType = "001ç";
    private String costumerType = "002ç";
    private String saleType = "003ç";

    Logger logs = Logger.getLogger("sales");

    public SystemDataService() {
        systemDataDao = new SystemDataDao();
        costumers = new ArrayList<>();
        salesmen = new ArrayList<>();
        saleList = new ArrayList<>();
    }

    private void populateAll() throws PatternFileException {
        populateSalesList();
        populateSalesmenList();
        populateCostumerList();
    }

    private void clearAll() {
        allData.clear();
        salesmen.clear();
        saleList.clear();
        costumers.clear();
    }

    public Map<String, List<String>> getAllData() {
        return allData;
    }

    public void setAllData(Map<String, List<String>> allData) {
        this.allData = allData;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }

    public List<Salesman> getSalesmen() {
        return salesmen;
    }

    public List<Costumer> getCostumers() {
        return costumers;
    }

    public void watchDirectory() throws Exception {

        logs.info("Reading the files...");
        allData = systemDataDao.readFile();
        populateAll();
        logs.info("Populating the lists");
        generateRegisters();
        logs.info("Registers generated at " + Paths.get(systemDataDao.getPath().getProperty("homepath")) + "/data/out");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path dirToWatch = Paths.get(systemDataDao.getPath().getProperty("homepath") + systemDataDao.getPathDataIn());
        dirToWatch.register(watchService, ENTRY_CREATE);
        WatchKey watchKey;
        while (true) {
            logs.info("Watching for new file");
            watchKey = watchService.take();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                WatchEvent.Kind<?> eventType = event.kind();
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();
                if (eventType == ENTRY_CREATE) {
                    logs.info(fileName + " created");
                    clearAll();
                    allData = systemDataDao.readFile();
                    logs.info("reading new files...");
                    populateAll();
                    logs.info("Populating the lists...");
                    generateRegisters();
                    logs.info("Registers generated at " + Paths.get(systemDataDao.getPath().getProperty("homepath")) + "/data/out");
                }
            }
            boolean validReset = watchKey.reset();
            if (!validReset)
                break;
        }
    }

    public List<String> getAllParseLines() throws PatternFileException {

        List<String> parseLines = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        List<List<String>> allDataValues = new ArrayList<>(getAllData().values());
        allDataValues.forEach(lines::addAll);

        for (String line : lines) {
            if (line.contains(" 0")) {
                String[] lineArray = line.split(" 0");
                String concatLine = 0 + lineArray[1];
                parseLines.add(lineArray[0]);
                parseLines.add(concatLine);
            } else {
                parseLines.add(line);
            }
        }
        for (String parseLine : parseLines) {
            if (parseLine.contains(salesmanType) || parseLine.contains(costumerType) || parseLine.contains(saleType)) {
                if (parseLine.chars().filter(ch -> ch == 'ç').count() != 3) {
                    throw new PatternFileException("\n" +
                            "Pattern inconsistencies were found in the input file. The system cannot read the file. Delimiter (ç) not found.");
                }
            } else {
                throw new PatternFileException("\n" +
                        "Inconsistencies were found in the input file. Delimiter 001ç, 002ç or 003ç is out pattern. The system cannot read the file.");
            }
        }
        return parseLines;
    }

    public List<String> returnDataByType(String dataType) throws PatternFileException {

        List<String> allParsedLines = getAllParseLines();
        List<String> dataByType = new ArrayList<>();
        for (String a : allParsedLines) {
            if (a.contains(dataType)) {
                dataByType.add(a);
            }
        }
        return dataByType;
    }


    public void populateSalesmenList() throws PatternFileException {
        List<String> salesmanAux = returnDataByType(salesmanType);
        salesmanAux.forEach(
                lineData -> {
                    String[] array = lineData.split("ç");
                    Salesman salesman = new Salesman();
                    salesman.setCpf(Long.parseLong(array[1]));
                    salesman.setName(array[2]);
                    salesman.setSalary(Double.parseDouble(array[3]));
                    salesmen.add(salesman);
                }
        );
    }

    public void populateCostumerList() throws PatternFileException {
        List<String> costumersAux = returnDataByType(costumerType);
        costumersAux.forEach(
                lineData -> {
                    String[] array = lineData.split("ç");
                    Costumer costumer = new Costumer();
                    costumer.setCnpj(Long.parseLong(array[1]));
                    costumer.setName(array[2]);
                    costumer.setBusinessArea((array[3]));
                    costumers.add(costumer);
                }
        );
    }

    public void populateSalesList() throws PatternFileException {
        List<String> salesAux = returnDataByType(saleType);
        salesAux.forEach(
                lineData -> {
                    String[] arraySplited = lineData.split("ç");
                    List<SaleDetails> saleDetailsList = new ArrayList<>();
                    Sale sale = new Sale();
                    sale.setSaleId(Integer.parseInt(arraySplited[1]));
                    sale.setName(arraySplited[3]);
                    String[] sales = arraySplited[2].split(",");
                    for (String s : sales) {
                        String[] eachSale = s.split("-");
                        SaleDetails saleDetails = new SaleDetails(Integer.parseInt(eachSale[0].replace("[", "")),
                                Integer.parseInt(eachSale[1]),
                                Double.parseDouble(eachSale[2].replace("]", "")),
                                arraySplited[3],
                                Integer.parseInt(arraySplited[1])
                        );
                        saleDetailsList.add(saleDetails);
                    }
                    sale.setSales(saleDetailsList);
                    saleList.add(sale);
                });
    }

    public List<Integer> returnMostExpensiveSale() {

        Map<Integer, Double> idAndTotalOfSale = new HashMap<>();
        Double total = 0.0;
        Integer id = 0;
        for (Sale sale : getSaleList()) {
            for (SaleDetails saleDetails : sale.getSales()) {
                total = (saleDetails.getItemPrice() * saleDetails.getItemQuantity()) + total;
                id = saleDetails.getSaleId();
            }
            idAndTotalOfSale.put(id, total);
            total = 0.0;

        }
        Double maxValueMap = Collections.max(idAndTotalOfSale.values());
        List<Integer> mostExpensiveValues = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : idAndTotalOfSale.entrySet()) {
            if (entry.getValue().equals(maxValueMap)) {
                mostExpensiveValues.add(entry.getKey());
            }
        }
        return mostExpensiveValues;
    }

    public String worstSalesmanEver() {
        List<String> allNames = new ArrayList<>();
        Map<String, List<Sale>> listaDeSalesPorPerson = new HashMap<>();
        Double total = 0.0;

        for (Sale sale : getSaleList()) {
            allNames.add(sale.getName());
        }

        List<String> allNamesDistinct = allNames.stream().distinct().collect(Collectors.toList());
        for (String name : allNamesDistinct) {
            List<Sale> salesByPerson = new ArrayList<>();
            for (Sale sale : getSaleList()) {
                if (name.equals(sale.getName())) {
                    salesByPerson.add(sale);
                }
            }
            listaDeSalesPorPerson.put(name, salesByPerson);
        }
        Map<String, Double> nameAndTotalOfSales = new HashMap<>();

        for (Map.Entry<String, List<Sale>> entry : listaDeSalesPorPerson.entrySet()) {
            for (Sale sale : entry.getValue()) {
                for (SaleDetails saleDetails : sale.getSales()) {
                    total = (saleDetails.getItemPrice() * saleDetails.getItemQuantity()) + total;
                }
                nameAndTotalOfSales.put(entry.getKey(), total);
                total = 0.0;
            }
        }
        return Collections.min(nameAndTotalOfSales.entrySet(), Map.Entry.comparingByValue()).getKey();

    }

    public void generateRegisters() {
        List<String> registers = new ArrayList<>();
        registers.add("Amount Of Clients | " + getCostumers().size() + "\n");
        registers.add("Amount of Salesmen | " + getSalesmen().size() + "\n");
        registers.add("ID of Most Expensive Sale | " + returnMostExpensiveSale() + "\n");
        registers.add("Worst Salesman Ever | " + worstSalesmanEver() + "\n");
        systemDataDao.writeFile(registers);
    }


}
