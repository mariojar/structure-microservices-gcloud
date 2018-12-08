package it.lancio.pilot.utils;

public enum GitProperties {
		
		MAIL_USER_BUILD("git.build.user.email",false),
		MAIL_USER_COMMIT("git.commit.user.email",false),
		USER("git.build.user.name",true),
		USER_COMMIT("git.commit.user.name",true),
		BRANCH("git.branch",true),
		ID("git.commit.id",true),
		TIME_BUILD("git.build.time",true),
		TIME_COMMIT("git.commit.time",true),
		MESSAGE_COMMIT("git.commit.message.full",true),
		MESSAGE_COMMIT_SHORT("git.commit.message.short",false),
		BUILD_VERSION("git.build.version",true),
		ID_DESCRIBE_SHORT("git.commit.id.abbrev",false),
		ID_DESCRIBE("git.commit.id.describe",false),
		HOST("git.build.host",true),
		DIRTY("git.dirty",false),
		ORIGIN("git.remote.origin.url",false),
		TAG("git.closest.tag.name",false),
		DESCRIBE("git.commit.id.describe-short",false),
		COMMIT_COUNT("git.closest.tag.commit.count",false),
		TAGS("git.tags",false);
		
		private String key;
		
		private boolean toDisplay;
		
		public boolean isToDisplay() {
			return toDisplay;
		}

		public String getKey() {
			return key;
		}

		GitProperties(String key,boolean toDisplay){
			this.key=key;
			this.toDisplay=toDisplay;
		}
}
