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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestOFInfos {
    public static class Instituicao {
        private String nome;
        private String status;
        private String logoUrl;

        public Instituicao(String nome, String status, String logoUrl) {
            this.nome = nome;
            this.status = status;
            this.logoUrl = logoUrl;
        }

        public String getNome() { return nome; }
        public String getStatus() { return status; }
        public String getLogoUrl() { return logoUrl; }
    }

    public static List<Instituicao> getInstituicoes() {
        List<Instituicao> instituicoes = new ArrayList<>();
        String url = "https://data.directory.openbankingbrasil.org.br/participants";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(URI.create(url));
            request.addHeader("Accept", "application/json");

            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);

                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> participantes = objectMapper.readValue(
                        jsonResponse,
                        new TypeReference<List<Map<String, Object>>>() {}
                );

                for (Map<String, Object> participante : participantes) {
                    String nome = (String) participante.get("OrganisationName");
                    String status = (String) participante.get("Status");
                    String logoUrl = null;

                    List<Map<String, Object>> authorisationServers =
                            (List<Map<String, Object>>) participante.get("AuthorisationServers");

                    if (authorisationServers != null && !authorisationServers.isEmpty()) {
                        logoUrl = (String) authorisationServers.get(0).get("CustomerFriendlyLogoUri");
                    }

                    instituicoes.add(new Instituicao(nome, status, logoUrl));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instituicoes;
    }
}