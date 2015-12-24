# RxMVP Android

This is a sample project to demonstrate various
ways to use the Model-View-Presenter pattern on Android.

It has a focus on how to integrate cleanly in difference ways
with RxJava and its subscriptions.

## Notes

- I deviate slightly from the strict MVP pattern by letting the
views format the model data for themselves. This allows the presenters
to be re-used across various views.

- There are 3 activities that use the same presenter but in different ways
    - One activity uses a standard base activity to `start()` and `finish()` the presenter
        - NOTE the presenter holds the subscriber and `unsubscribes` in `finish()`
    - Another activity binds the observable to the activity lifecycle
        - NOTE the presenter passes back an observable bypassing the internal one
    - The last activity uses a `fatter` custom view with a presenter attached
        - This is the pattern I tend to move towards for modularity
        

### If you have any questions or comments please open an issue
