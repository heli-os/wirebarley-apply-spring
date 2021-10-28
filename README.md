# WireBarley Apply
* 작업: 2021-10-28(목) ~ 2021-10-29(금)
* 문제 레퍼런스: https://github.com/wirebarley/apply/blob/master/coding_test.md

## 프로젝트 구조

```
wirebarley-apply-spring
├── README.md
├── build.gradle.kts
└── src
    ├── main
    │   ├── kotlin
    │   │   └── apply
    │   │       └── aaron
    │   │           └── wirebarley
    │   │               ├── WireBarleyApplySpringApplication.kt
    │   │               ├── client
    │   │               │   └── currenylayer
    │   │               │       ├── CurrencyLayerClient.kt
    │   │               │       ├── CurrencyLayerClientConfiguration.kt
    │   │               │       └── dto.kt
    │   │               ├── configuration
    │   │               │   └── WireBarleyApplySpringConfiguration.kt
    │   │               ├── domain
    │   │               │   └── Exchanges.kt
    │   │               ├── interface
    │   │               │   ├── ExRateRestController.kt
    │   │               │   └── dto.kt
    │   │               ├── service
    │   │               │   └── exchange
    │   │               │       ├── ExCalculator.kt
    │   │               │       └── ExRateFetcher.kt
    │   │               └── util
    │   │                   ├── objectx.kt
    │   │                   └── timex.kt
    │   └── resources
    │       ├── META-INF
    │       │   └── additional-spring-configuration-metadata.json
    │       ├── application.yml
    │       ├── static
    │       │   └── app.js
    │       └── templates
    │           └── index.html
    └── test
        └── kotlin
            └── apply
                └── aaron
                    └── wirebarley
                        ├── WireBarleyApplySpringApplicationTests.kt
                        └── exchange
                            ├── ExCalculatorTest.kt
                            └── ExRateFetcherTest.kt
```

## 주요 기능
### REST API

#### 1. 환율정보 조회
`GET` `/exchange/api/v1/rate/{sourceCurrency}/{destinationCurrency}`
* Request
    * sourceCurrency: USD
    * destinationCurrency: KRW, JPY, PHP
* Response
    ```json
    {
      "sourceCurrency": String, // USD, KRW, JPY, PHP
      "destinationCurrency": String, // USD, KRW, JPY, PHP
      "exRateAmount": Double,
      "exRateLastUpdateEpochMillis": Long
    }
    ``` 

#### 2. 수취금액 계산
`GET` `/exchange/api/v1/rate/calculate/{sourceCurrency}/{destinationCurrency}?sourceAmount={sourceAmount}`
* Request
    * sourceCurrency: USD
    * destinationCurrency: KRW, JPY, PHP
    * sourceAmount: 100.0, 200.0, 300.0, ...
* Response
    ```json
    {
      "transaction": {
        "amount": Double,
        "currency": String, String, // USD, KRW, JPY, PHP
      },
      "exRateLastUpdateEpochMillis": Long
    }
    ``` 

### Demo Page
* SpringBoot Project 실행 후 http://localhost:8080/ 접속

