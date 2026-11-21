# TrabalhoArquitetura

Sistema de estacionamento para faculdade, feito para demonstrar a **arquitetura hexagonal**
(domínio no centro, portas em `application/port`, adaptadores em `adapter`).

## Endpoints

| Recurso   | Método e rota                                   | Descrição                                          |
|-----------|-------------------------------------------------|----------------------------------------------------|
| Vagas     | `POST /api/vagas`                               | Cadastra vaga (`numero`, `tipo`)                   |
|           | `GET /api/vagas[?status=LIVRE\|OCUPADA]`        | Lista vagas                                        |
|           | `GET /api/vagas/{id}`                           | Busca vaga                                         |
| Veículos  | `POST /api/veiculos`                            | Cadastra veículo (`placa`, `modelo`, `cor`, `tipo`)|
|           | `GET /api/veiculos`                             | Lista veículos                                     |
|           | `GET /api/veiculos/{placa}`                     | Busca por placa                                    |
| Fluxo     | `POST /api/estacionamentos/entradas?placa=&vagaId=` | Registra entrada (gera ticket)                 |
|           | `POST /api/estacionamentos/{ticketId}/saida`    | Registra saída (finaliza ticket, libera vaga)      |
| Tickets   | `GET /api/tickets[?status=ATIVO\|FINALIZADO]`   | Lista tickets                                      |
|           | `GET /api/tickets/{id}`                         | Busca ticket                                       |
| Pagamentos| `POST /api/pagamentos`                          | Paga ticket finalizado (`ticketId`, `formaPagamento`: `DINHEIRO`, `PIX`, `CARTAO_CREDITO` ou `CARTAO_DEBITO`) |
|           | `GET /api/pagamentos`                           | Lista pagamentos                                   |
|           | `GET /api/pagamentos/{id}`                      | Busca pagamento                                    |
|           | `GET /api/pagamentos/ticket/{ticketId}`         | Pagamento de um ticket                             |
| Relatório | `GET /relatorios/faturamento?inicio=&fim=`      | Faturamento no período (ISO date-time)             |

## Fluxo completo

1. Cadastrar vaga e veículo
2. `POST /api/estacionamentos/entradas?placa=ABC1D23&vagaId=1`
3. `POST /api/estacionamentos/{ticketId}/saida`
4. `POST /api/pagamentos` com `{"ticketId": 1, "formaPagamento": "PIX"}` (valores aceitos: `DINHEIRO`, `PIX`, `CARTAO_CREDITO`, `CARTAO_DEBITO`)
5. `GET /relatorios/faturamento?...`

## Regras

- A placa é normalizada (`trim` + maiúsculas) na entrada.
- O veículo precisa estar cadastrado; o tipo vem do cadastro e deve ser compatível com a vaga
  (carro só em vaga de carro, moto só em vaga de moto).
- Códigos HTTP: `404` vaga/ticket/veículo/pagamento inexistente; `409` conflito de estado
  (vaga ocupada, veículo já estacionado, tipo incompatível, ticket já finalizado/pago);
  `400` dados inválidos.

## Diagramas

- Pacotes: `diagramas/diagrama-de-pacotes-Estacionamento.png`
- Componentes: `diagramas/diagrama-de-componentes-Estacionamento.png`
- Implantação: `diagramas/DiagramaDeImplantaçãoEstacionamento.pdf`
