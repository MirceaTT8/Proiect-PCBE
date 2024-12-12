package com.javazerozahar.stock_exchange.controllers;

import com.javazerozahar.stock_exchange.model.dto.PortfolioDTO;
import com.javazerozahar.stock_exchange.service.PortfolioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/portfolios")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PortfolioController {

    private PortfolioService portfolioService;

    @GetMapping
    public ResponseEntity<List<PortfolioDTO>> getPortfolios(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long stockId
    ) {
        return new ResponseEntity<>(portfolioService.getPortfolios(userId, stockId), HttpStatus.OK);
    }

    @GetMapping("/{portfolioId}")
    public ResponseEntity<PortfolioDTO> getStock(@PathVariable("portfolioId") Long portfolioId) {
        return new ResponseEntity<>(portfolioService.getPortfolio(portfolioId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PortfolioDTO> createPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        return new ResponseEntity<>(portfolioService.addPortfolio(portfolioDTO), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<PortfolioDTO> updatePortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        return new ResponseEntity<>(portfolioService.updatePortfolio(portfolioDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePortfolio(@PathVariable("portfolioId") Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
    }
}
