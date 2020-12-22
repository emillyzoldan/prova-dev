package com.emilly.desafiobackend;

import com.emilly.desafiobackend.service.SystemDataService;

public class Main {
    public static void main(String[] args) throws Exception {
        SystemDataService systemDataService = new SystemDataService();
        systemDataService.watchDirectory();
    }

}
