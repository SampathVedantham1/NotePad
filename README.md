# Note-pad application using *MVVM, Room, Jetpack Compose*:
### Introduction :
- This commit encompasses all the code essential for the notepad application.
### Technologies used :
- Dagger Hilt
- Jetpack compose
- Kotlin
- Room database
### References :
- _**Screenshots**_ :
    - [All notes screen](https://drive.google.com/file/d/1rz01mOZGVt3dC5vPj0fRDPcfG1lNyH_p/view?usp=sharing)
    - [All notes screen with filters](https://drive.google.com/file/d/1VA2MWpCQ6vv9rWFQa0DyBfoDK8IR2bxF/view?usp=sharing)
    - [Note screen](https://drive.google.com/file/d/1xrYB6Dyc_NvQy617R5OfSElkrSSxpXgS/view?usp=sharing)
    - [Empty Title error toast](https://drive.google.com/file/d/1Fum_wWC2FUeMnfc_9Hs6rQzUBywN1yLA/view?usp=sharing)
    - [Add new Note screen](https://drive.google.com/file/d/14gduGj_rvpsyBW6Y24ummWbD6V7kfWBV/view?usp=sharing)
- _**Video**_ :
    - [Video reference](https://drive.google.com/file/d/1Qootk4F-VZXNwGlgwZ_YdQ7tBpqv3uyi/view?usp=sharing)
### Project Structure :
- At the outer level, it consists of three modules:
    - #### DependencyInjection module :
        - Involves code related to dependency injection using Dagger Hilt.
    - #### note_feature :
        - Consists of three distinct modules. They are
            1. data :
                - Encompasses code related to the Room database.
            3. domain :
                - Involves code related to business logic.
            5. screens :
                - Holds code related to all screens.
    - #### ui.theme:
        - Includes all the colors and themes used in the application.
### Data module :
- The data module consists two different modules. They are
    1. #### data_source:
        - Consists a file named **NoteDataAccessObject** which is a DAO(Data access object). This file is responsible for defining the methods that access the database.
        - Includes a file named **NoteDataBase** which is a abstract class exteneding to RoomDatabase class and annotated with annotation @Database.
    2. #### repository :
        - Holds a file named **NoteRepositoryImpl**. Implementation of the NoteRepository interface, responsible for interacting with the data source.
### Domain module :
- The domain module consists of 4 sub modules. They are
    - #### model :
        - Created a model class named **Note** using the **@Entity** annotation.
    - #### repository :
        - Created an interface named **NoteRepository** for managing Note entities.
    - #### use_case :
        - #### AddOrEditNote :
            - Represents a use case for adding or editing a Note in the repository.
              - #### DeleteNote :
            - Represents a use case for deleting a Note from the repository.
              - #### FetchAllNotes :
            - Represents a use case for fetching and ordering all notes from the repository based on various filters.
              - #### FetchNoteById :
            - Represents a use case for fetching a Note by its ID from the repository.
              - #### NoteUseCases :
            - Data class to group and manage different use cases related to Note entities.
    - #### util :
        - Created a sealed class named **NoteOrderByFilter** representing the filter criteria for ordering notes such as _Title , date and color_.
        - Created a sealed class named **OrderTypeFilter** representing the order type filter for sorting items such as _Ascending / Descending_.
### Screen module :
- Consists of three modules. They are
    - #### add_edit_note_screen :
        - Created a sealed class named **AddEditEvent** representing different events that can occur in the Add/Edit Note screen.
        - Establishes a view model class named **AddEditViewModel** that handles the logic and state of the add/edit note screen.
        - Defines a data class that represents the state of a text input field.
        - Includes a module named **composable** , which consists of the composable files named **AddEditNote** and **HintTextField**.
    - #### all_notes_screen :
        -  Created a sealed class named **NoteEvents** representing different events that can occur in the All Notes screen.
        -  Defines a data class named **NotesState** representing the state of the Notes list.
        -  Established a view model named **NotesViewModel** that represents the state of the notes screen.
        -  Includes a module named **composable** , which consists of the composable files named **FilterRadioButton**, **NoteItem**, **NotesScreen** and **OrderSection**.
    - #### util :
        - Created a sealed class representing different screen navigations in the application.
    - Includes **MainActivity** which is the launching activity of the application.
  ### Dependency Injection module :
    -  Created a file named **AppModule** for providing application-level dependencies.