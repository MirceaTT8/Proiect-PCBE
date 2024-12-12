package com.javazerozahar.stock_exchange.service;

import com.javazerozahar.stock_exchange.converters.PortfolioConverter;
import com.javazerozahar.stock_exchange.exceptions.PortfolioNotFoundException;
import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.model.entity.Order;
import com.javazerozahar.stock_exchange.model.entity.Portfolio;
import com.javazerozahar.stock_exchange.model.entity.Stock;
import com.javazerozahar.stock_exchange.repository.PortfolioRepository;
import com.javazerozahar.stock_exchange.utils.Patcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioConverter portfolioConverter;
    private final Patcher patcher;

    public List<PortfolioDTO> getPortfolios(Long userId, Long stockId) {
        if (userId == null && stockId == null) {
            return portfolioRepository.findAll().stream().map(portfolioConverter::toPortfolioDTO).toList();
        }

        if (userId != null && stockId == null) {
            return portfolioRepository.findAllByUserId(userId).stream().map(portfolioConverter::toPortfolioDTO).toList();
        }

        if (userId == null) {
            return portfolioRepository.findByStockId(stockId).stream().map(portfolioConverter::toPortfolioDTO).toList();
        }

        return portfolioRepository.findByUserIdAndStockId(userId, stockId).stream().map(portfolioConverter::toPortfolioDTO).toList();
    }

    @Transactional
    public Portfolio getPortfolioByUserIdAndStock(Long userId, Stock stock) {
        return portfolioRepository.findByUserIdAndStockWithLock(userId, stock)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found for " + userId + " : " + stock));
    }

    @Transactional
    public Portfolio getPortfolioByUserIdAndStockWithLock(Long userId, Stock stock) {
        return portfolioRepository.findByUserIdAndStockWithLock(userId, stock)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found\""));
    }

    @Transactional
    public Portfolio updatePortfolio(Order order, double quantity) {
        Portfolio portfolio = portfolioRepository.findByUserIdAndStockWithLock(order.getUser().getId(), order.getBoughtStock()).orElseThrow();
        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);
        return portfolio;
    }

    public PortfolioDTO updatePortfolio(PortfolioDTO portfolioDTO) {
        PortfolioDTO portfolioToBeUpdated = getPortfolioDTO(portfolioDTO.getId());
        Portfolio portfolio = portfolioConverter.toPortfolio(patcher.patch(portfolioToBeUpdated, portfolioDTO));
        portfolioRepository.save(portfolio);
        return portfolioConverter.toPortfolioDTO(portfolio);
    }

    private PortfolioDTO getPortfolioDTO(Long id) {
        return portfolioRepository.findById(id).map(portfolioConverter::toPortfolioDTO)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found"));
    }

    @Transactional
    public void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(Long portfolioId) {
        portfolioRepository.deleteById(portfolioId);
    }

    public PortfolioDTO addPortfolio(PortfolioDTO portfolioDTO) {
        return portfolioConverter.toPortfolioDTO(portfolioRepository.save(portfolioConverter.toPortfolio(portfolioDTO)));
    }
}
