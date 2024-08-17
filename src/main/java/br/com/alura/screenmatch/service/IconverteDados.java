package br.com.alura.screenmatch.service;

public interface IconverteDados {
    <T> T obeterDados(String json, Class<T> tClass);
}
