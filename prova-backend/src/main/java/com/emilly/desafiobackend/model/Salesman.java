package com.emilly.desafiobackend.model;

public class Salesman {

    private final Integer SALESMAN_FORMAT_ID = 001;
    private long cpf;
    private String name;
    private Double salary;

    public Salesman() {
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Salesman{" +
                "SALESMAN_FORMAT_ID=" + SALESMAN_FORMAT_ID +
                ", cpf=" + cpf +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
