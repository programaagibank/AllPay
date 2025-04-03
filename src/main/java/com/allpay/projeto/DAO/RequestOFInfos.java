package com.allpay.projeto.DAO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class RequestOFInfos {

    public static void jsonRequest() {
        String url = "https://data.directory.openbankingbrasil.org.br/participants";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(URI.create(url));

            // Adicionando header para aceitar JSON
            request.addHeader("Accept", "application/json");

            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);
                ObjectMapper objectMapper = new ObjectMapper();

                // A resposta é um array direto, então desserializamos para List<Map>
                List<Map<String, Object>> participantes = objectMapper.readValue(
                        jsonResponse,
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                // Iterando sobre os participantes
                for (Map<String, Object> participante : participantes) {
                    System.out.println("Nome: " + participante.get("OrganisationName"));
                    System.out.println("Status: " + participante.get("Status"));
                    List<Map<String, Object>> authorisationServers =
                            (List<Map<String, Object>>) participante.get("AuthorisationServers");

                    if (authorisationServers != null && !authorisationServers.isEmpty()) {
                        // Pegando o primeiro servidor (geralmente contém o logo)
                        Map<String, Object> firstServer = authorisationServers.get(0);

                        // Obtendo a URL do logo
                        String logoUrl = (String) firstServer.get("CustomerFriendlyLogoUri");
                        System.out.println("Logo: " + (logoUrl != null ? logoUrl : "Não disponível"));
                    } else {
                        System.out.println("Logo: Nenhum servidor de autorização encontrado.");
                    }

                    System.out.println("---");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        jsonRequest();
    }
}