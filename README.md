Always check twice what you are commiting.
Never commit .jar files!
You should take jar from target/ directory instead when building the docker image.
tokenotil.env contains the secrets!

You should remove target/ from the git history — ask chatgpt how to do that or google it

If you use intellij ultimate learn "database" tab to connect to your dev database.
Instead of hibernate ddl auto you should use flyway.