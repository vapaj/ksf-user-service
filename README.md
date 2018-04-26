# ksf-user-service

A minimalistic app for managing KSF Media user accounts.

The app is written with [ClojureScript](http://i.imgur.com/NbWfuxL.png) and [Reagent](https://reagent-project.github.io/). [Bootstrap](https://getbootstrap.com/) is used for styling.

## Development mode

It is expected that you have [Leiningen](https://leiningen.org/) installed.

When running the app in development mode, the use of `figwheel` is couraged.

Start `fighweel` in project root with:

```
lein figwheel
```

The server will be available at [http://localhost:3449](http://localhost:3449).

## Notes

- As no logging out mechanism is provided by the API, `auth-token` is stored in plain ol' variable, so it's gone after the page reloads. Local storage *could* be used for saving it (and removing it on logout)
- Other than login, there is no handling for HTTP errors. A simple generic notification system for HTTP errors could be done with e.g. `core.async` channels quite nicely
- I really need to practise my Swedish
