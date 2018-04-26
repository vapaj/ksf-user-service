# ksf-user-service

A minimalistic app for managing KSF Media user accounts.

The app uses [Reagent](https://reagent-project.github.io/) and [Bootstrap](https://getbootstrap.com/) for styling.

## Development mode

When running the app in development mode, the use of `figwheel` is couraged.

Start `fighweel` in project root with:

```
lein figwheel
```

The server will be available at [http://localhost:3449](http://localhost:3449).

## Building for release

```
lein do clean, uberjar
```
