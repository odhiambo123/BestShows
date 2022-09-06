# BestShows
Consuming API.
- Create a new App with empty activity
- Add depencencies and plugins
  - app level: 
    - [gist](https://gist.github.com/17f45e7f888c048ba780c16ff22ce11d)
  - project level: 
    - [gist](https://gist.github.com/8367ce8e25d5d58e6a4b6f130663cb97)

- Create the package folder structure
  - app
  - data
    -inside data create `remote` and `model` packages
  - di
  - repo
  - ui
    - inside the `ui` add the `detail` and `activities`
  - util
  
  
- Create the constants in the util packge
- create classes in the `model` from JSON you get from the `remote end point`
- create the Api inteface class inside the `remote`
- create `application` class in side the `app`
- crea a `module` class in the `di`
- Create the `repository` class inside the `repo`
- inside the `ui` 
- create the layour file to show the cards  using cardview with shildren
  - Cardview
    - linearlayout
      constraint layout
        imageview
      textview
-Create the adapter
  make sure to enable viewBinding in the build.gradle app level
- Create viewmodel

adjust the activity main xml
