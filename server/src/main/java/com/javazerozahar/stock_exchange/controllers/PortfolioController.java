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

    @PostMapping
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public void createPortfolio(@RequestBody PortfolioDTO portfolioDTO) {

    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public void updatePortfolio(@RequestBody PortfolioDTO portfolioDTO) {
//        return new ResponseEntity<>(portfolioService.updatePortfolioDTO(portfolioDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{portfolioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePortfolio(@PathVariable("portfolioId") Long portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
    }
}
