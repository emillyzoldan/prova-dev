package com.emilly.desafiobackend.dao;

import com.emilly.desafiobackend.exceptions.FileReaderException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SystemDataDao {

    private String pathDataIn = "/data/in";
    private String pathDataOut = "/data/out";
    private List<List<String>> allLines = new ArrayList<>();

    public String getPathDataIn() {
        return pathDataIn;
    }

    public Properties getPath() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/path.properties")) {
            Properties prop = new Properties();
            prop.load(inputStream);
            return prop;
        } catch (FileNotFoundException e) {
            throw new FileReaderException("An error was encountered while reading the file: " + e.getMessage());
        } catch (IOException io) {
            throw new FileReaderException("An error ocurred while opening the file: " + io.getMessage());
        }
    }

    public Map<String, List<String>> readFile() throws IOException {
        final String FOLDER_PATH = getPath().getProperty("homepath") + pathDataIn;
        Path folderPath = Paths.get(FOLDER_PATH);
        Map<String, List<String>> linesOfFiles = new TreeMap<>();
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
            throw new FileReaderException("An error ocurred while reading the files: " + ex.getMessage());
        }
        for (String file : fileNames) {
            try {
                if (file.contains(".dat")){
                List<String> linesa = Files.readAllLines(folderPath.resolve(file));
                linesOfFiles.put(file, linesa);
                allLines.add(linesa);
                }
            } catch (IOException e) {
                throw new FileReaderException("An error ocurred while reading the files: " + e.getMessage());
            }

        }
        return linesOfFiles;
    }

    public void writeFile(List<String> dataForWrite) {
        try {
            File myFile = new File(getPath().getProperty("homepath") + pathDataOut + "/registers.done.dat");
            FileWriter myWriter = new FileWriter(myFile);
            for (String data : dataForWrite) {
                myWriter.write(data);
            }
            myWriter.close();
        } catch (IOException ioException) {
            throw new FileReaderException("An error ocurred while writing to the file: " + ioException.getMessage());
        }

    }

}

