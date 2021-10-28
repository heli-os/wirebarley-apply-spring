class ExRateApi {

    static fetchExRate(sourceCurrency, destinationCurrency, callback) {
        const http = new XMLHttpRequest()
        http.open("GET", `/exchange/api/v1/rate/${sourceCurrency}/${destinationCurrency}`, true)
        http.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                const response = JSON.parse(this.responseText)
                callback(response)
            }
        }
        http.send()
    }

    static calculateExRate(sourceCurrency, destinationCurrency, sourceAmount, callback) {
        const http = new XMLHttpRequest()
        http.open("GET", `/exchange/api/v1/rate/calculate/${sourceCurrency}/${destinationCurrency}?sourceAmount=${sourceAmount}`, true)
        http.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                const response = JSON.parse(this.responseText)
                callback(response)
            }
        }
        http.send()
    }
}

class App {
    constructor() {
        this.sourceCurrencyInput = document.getElementById('sourceCurrency')
        this.sourceAmountInput = document.getElementById('sourceAmount')
        this.destinationCurrencySelectBox = document.getElementById('destinationCurrency')
        this.exRateText = document.getElementById('exRate')
        this.exRateMiscText = document.getElementById('exRateMisc')
        this.calculateExRateBtn = document.getElementById('calculateExRate')
        this.transactionCalculateResultText = document.getElementById('transactionCalculateResult')
        this.transactionCalculateResultMiscText = document.getElementById('transactionCalculateResultMisc')
    }

    fetchRate() {
        ExRateApi.fetchExRate(this.sourceCurrencyInput.value, this.destinationCurrencySelectBox.value, response => {
            const {sourceCurrency, destinationCurrency, exRateAmount, exRateLastUpdateEpochMillis} = response
            this.exRateText.innerText = `${exRateAmount.toLocaleString('ko-KR', {maximumFractionDigits: 2})} ${sourceCurrency}/${destinationCurrency}`
            this.exRateMiscText.innerText = `(last update: ${new Date(exRateLastUpdateEpochMillis).toISOString()})`
        })
    }

    calculateExRate() {
        ExRateApi.calculateExRate(this.sourceCurrencyInput.value, this.destinationCurrencySelectBox.value, this.sourceAmountInput.value, response => {
            const {transaction, exRateLastUpdateEpochMillis} = response
            this.transactionCalculateResultText.innerText = `수취금액은 ${transaction['amount'].toLocaleString('ko-KR', {maximumFractionDigits: 2})} ${transaction['currency']} 입니다.`
            this.transactionCalculateResultMiscText.innerText = `(계산 기준 시간: ${new Date(exRateLastUpdateEpochMillis).toISOString()})`
        })
    }
}

// ========================

window.onload = function () {
    const app = new App()
    app.fetchRate()

    app.destinationCurrencySelectBox.addEventListener('change', () => {
        app.fetchRate()
    })

    app.calculateExRateBtn.addEventListener('click', () => {
        if (!app.sourceAmountInput.value
            || app.sourceAmountInput.value < 0
            || app.sourceAmountInput.value > 10000) {
            alert("송금액이 바르지 않습니다")
            return
        }
        app.calculateExRate()
    })
}
