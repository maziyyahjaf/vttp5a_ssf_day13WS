# Git commands

In project root folder
1. git init

Add files to staging
2. git add .

Commit files to local repo <br/>
3. git commit -m “initial commit” <br/>

Change branch name <br/>
4. git branch -m main <br/>

Link up remote repo to local repo <br/>
5. git remote add origin <repo url> <br/>

Push code up from local repo to remote repo<br/>
6. git push -u origin main<br/>

# Maven Compiler command<br/>

In project root folder <br/>
./mvnw clean <br/>

package the compiled bytecode classes into jar/war file
./mvnw package <br/>

delete target, re-download dependencies, recompile to class files, package to jar/war<br/>
./mvnw clean package <br/>

run the application in embedded localhost server <br/>
./mvnw spring-boot:run  (running the application with no arguments)

Run directly from Maven with
mvn spring-boot:run -Dspring-boot.run.arguments="--dataDir=/Users/maziyyah/documents/visa_vttp/02_SSF/workshops/day13contacts"

// to try figure out:
   <td th:text="${#dates.format(contact.dateOfBirth, 'dd-MM-yyyy')}"></td>