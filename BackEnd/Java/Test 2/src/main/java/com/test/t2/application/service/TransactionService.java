package com.test.t2.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.t2.domain.model.entities.Transaction;
import com.test.t2.infrastructure.adapter.out.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Método que obtiene el historial de transacciones de un usuario específico.
     * @param userId Id del usuario del cual se quiere obtener el historial.
     * @return Lista del historial de transacciones del usuario específicado.
     */
    public List<Transaction> getTransactionHistory(String userId) {
        return transactionRepository.findByUserId(userId);
    }

    /**
     * Método que obtiene transacciones de un usuario según su tipo (por ejemplo, "subscription" o "cancellation").
     * @param userId Id del usuario
     * @param type Tipo de transacción
     * @return Lista de transacciones filtradas por tipo
     */
    public List<Transaction> getTransactionsByType(String userId, String type) {
        return transactionRepository.findByUserIdAndType(userId, type);
    }
}