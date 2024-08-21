Smart Contact Manager is a web application developed using **Spring Boot** and **Thymeleaf** under the **MVC** architecture. It features robust Authentication and Authorization mechanisms, implemented with **Spring Security**, ensuring that only authorized users can access specific functionalities, effectively blocking unauthorized access.

The application leverages **Spring Data JPA** for efficient database interaction and CRUD operations. On the front end, the application leverages HTML and Tailwind CSS to create a responsive and visually appealing user interface. The interface supports both dark and light modes, enhancing user experience and accessibility.

Users can log in using **Google, GitHub**, or traditional email and password authentication, offering flexibility and convenience.Additionally, user emails are verified through a confirmation email, and if a user forgets their password, they can reset it via a link sent to their email. 

Images are stored on **Cloudinary** cloud-based service, ensuring efficient and reliable cloud storage.
This combination of technologies ensures that the Smart Contact Manager is both powerful and user-friendly.

---

### Configuration
  Properties Configuration
   ```properties
spring.application.name=smartcontactmanager

#mysql database configuration
spring.datasource.url = jdbc:mysql://localhost:3306/scm_db
spring.datasource.username=root
spring.datasource.password=Arbab33@Ahmad
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#jpa configuration
spring.jpa.show-sql=true
#if table is not created, this will create automatically update.
#Also if there is change in table, it will update that.
spring.jpa.hibernate.ddl-auto=update

#oauth2 client configuration
#google
spring.security.oauth2.client.registration.google.client-name=google
spring.security.oauth2.client.registration.google.client-id=695504357722-758oeej0t546v9i8eeef1hue2adfjm62.apps.googleusercontent.com
spring.security.oauth2.client.registration.google.client-secret=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
spring.security.oauth2.client.registration.google.scope=email,profile

#github configure
spring.security.oauth2.client.registration.github.client-name=github
spring.security.oauth2.client.registration.github.client-id=Iv23liusN6KbPSwiC82n
spring.security.oauth2.client.registration.github.client-secret=a09e13e116107e3071a30360b41711c8af6ecc2b
spring.security.oauth2.client.registration.github.scope=email,profile

#Cloudinary Configuration
cloudinary.cloud.name = du4xvylcx
cloudinary.api.secret = XrKBR1om-XZX7yCKkuwaupjsah0
cloudinary.api.key = 718917356674352

#MailStrap configuration
spring.mail.host = smtp.gmail.com
spring.mail.port = 587
spring.mail.username= arbab33ahmad
spring.mail.password= izey gdzf akjo mnda
spring.mail.properties..mail.smtp.auth = true
spring.mail.properties.mail.smtp.starttls.enable = true

server.port= 8080
```
---

### Register Page
![Register](https://github.com/user-attachments/assets/7e1e99bf-8fb4-4067-b9c6-1a973e54ffa2)

---

### Login Page
![Login](https://github.com/user-attachments/assets/13848de2-8358-4335-a4ce-0a1f8baf79f0)

---

### Profile Page
![Profile](https://github.com/user-attachments/assets/0d75994e-bb79-4193-94b5-299556a6b062)

---

### Add Contact Page
![Add Contacts](https://github.com/user-attachments/assets/3e8e8c5d-c432-49e4-b391-90471c8078ac)

---

### All Contacts Page
![All contacts](https://github.com/user-attachments/assets/a8a4d3b1-24ff-4622-bf31-b54b1afbabc6)


