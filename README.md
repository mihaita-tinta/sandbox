# sandbox
Docker compose sandbox to help you troubleshoot performance issues. More info [here](https://mihaita-tinta.medium.com/how-to-run-a-performance-test-on-your-application-42551fb62d38).

![diagram](http://www.plantuml.com/plantuml/png/ZOzDJWCn34RtEOKlm1oW2rGMQBP8dC0atxI1n0csmy_jcP5QZSG2klLxygTtD6VrKKjuqbRXPmoZktjww9qET89JXMkE5cRsakMqmoolY4x3e1QaEQXWyK0qAwV1fNoJSkqqEbchk8YGH9lyxzvsMS7ZbqE5opFq8qVOQYdU1yp3RxJ_KLXlOWZJDAqV48U3KbuMACJ98eGrDwELNuUkVGTgRt_bfFOfjrVCoWiBRt1NBqvYExyqrcJX24cZbXy0)

[comment]: <> (```puml)

[comment]: <> (@startuml)

[comment]: <> (Prometheus -> App : get metrics)

[comment]: <> (Gatling -> App : inject users)

[comment]: <> (note left)

[comment]: <> (  Gradually ramp up some users)

[comment]: <> (end note)

[comment]: <> (Prometheus -> App : get metrics)

[comment]: <> (App -> ExternalServices : request)

[comment]: <> (ExternalServices -> App : response)

[comment]: <> (... Execute different scenarios ...)

[comment]: <> (App -> Gatling : ok)

[comment]: <> (note left)

[comment]: <> (  Gradually ramp down)

[comment]: <> (end note)

[comment]: <> (Prometheus -> App : get metrics)

[comment]: <> (Grafana -> Prometheus : get dashboards)

[comment]: <> (@enduml)

[comment]: <> (```)
