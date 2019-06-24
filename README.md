# # MLChallenge
Projeto criado como estudo do consumo das API's do Mercado Livre

https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas 

## Arquitetura

MVVM + Repository Pattern, referência: https://developer.android.com/jetpack/docs/guide

O ViewModel fica responsável pelas lógicas de apresentação, tornando a view passiva

O Repositório atua como um datasource sendo o responsável pela obtenção dos dados

A separação acima favorece a testabilidade e a reutilização de código

## Tecnologias Utilizadas

* [Retrofit](https://square.github.io/retrofit/) - Requisições HTTP
* [AndroidViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Gerenciar estado da View
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Programação assincrona
* [Constraint/Design Libraries](https://developer.android.com/topic/libraries/support-library/features) - Criação de Layout 
* [Koin](https://insert-koin.io/) - Injeção de Dependências
* [Glide](https://bumptech.github.io/glide/) - Download de Imagens
* [Mockk](https://github.com/mockk/mockk) - Criação de objetos fake para testes
* [JUnit](https://github.com/junit-team/junit4) - Framework de testes
* [Core-testing](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Biblioteca utilitária para testes de LiveData
* [Kotlinx-coroutines-test](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md) - Biblioteca utilitária para testes de coroutines
