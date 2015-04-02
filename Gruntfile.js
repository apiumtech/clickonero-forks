module.exports = function(grunt) {

  grunt.initConfig({
    batch_git_clone: {
	    options: {
	      configFile:"repos.json",
	      npmInstall:true,
      	  bowerInstall:true,
          overWrite:true
	    }
  	}
  });

  grunt.loadNpmTasks('grunt-batch-git-clone');

  grunt.registerTask('default', ['batch_git_clone']);

};