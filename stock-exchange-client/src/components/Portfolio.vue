<template>
  <h1>portfolio example</h1>
  <div class="portfolio-container">
    <div class="stock-name">{{ stockSymbol }}</div>
    <div class="details-container">
        <div class="values-container">
            <div class="quantity">Ammount: {{ stockQuantity }}</div>
            <div class="euro-value">= {{ euroValue }} EUR</div>
        </div>
            <router-link :to="{ name: 'dashboard', params: {stockId: stockId} }">
                <button>Trade</button>
            </router-link>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

defineProps({
    stockId: {
        type: Number,
        default: 0,
    },
    stockSymbol: {
        type: String,
        default: 'Undefined s'
    },
    stockQuantity: {
        type: Number,
        default: 0
    },
    euroValue: {
        type: Number,
        default: 0
    },
});

</script>

<style scoped>
    .portfolio-container {
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        max-width: 500px;
        border: 3px solid darkgreen;
        background-color: green;
        border-radius: 15px;
        padding-top: 15px;
        padding-bottom: 15px;
        height: 150px;
        font-family: sans-serif;
    }
    .stock-name {
        background-color: lightgreen;
        border-radius: 15px;
        width: 220px;
        text-align: center;
        line-height: 150px;
        border: none;
        font-size: 40px;
    }
    .details-container {
        display: flex;
        flex-direction: column;
        justify-content:space-around;
        width: 220px;
    }
    .values-container {
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        background-color: lightgreen;
        border-radius: 15px;
        height: 80px;
        text-align: center;
        border: none;
    }
    .quantity {
        font-size: 25px;
    }
    button {
        background-color: white;
        border-radius: 15px;
        height: 50px;
        border: 3px solid dodgerblue;
        color: dodgerblue;
        font-size: 25px;
        width: 220px;
    }
    button:hover {
        background-color: dodgerblue;
        color: white;
    }
</style>