'use strict';

module.exports = function(grunt){
	var assets = 'src/main/resources/assets';
	var dist = 'src/main/resources/assets/dist';
	var watchFiles = {		
		serverJS: ['gruntfile.js'],
		clientViews: ['modules/**/views/*.html','modules/**/img/**', 'index.html' ],
		clientJS: ['modules/**/*.js', 'js/*.js'],
		clientCSS: ['modules/**/*.css','css/*css']
	}; 
	grunt.initConfig({
		pkg:grunt.file.readJSON('package.json'),
		watch: {
			options: {
				livereload: true,
			},
			express: {
				files:  [ 'server.js' ],
				tasks:  [ 'express:dev' ],
				options: {
					spawn: false
				}
			},
			serverJS: {
				files: watchFiles.serverJS,
				tasks: ['jshint'],
				options: {
					livereload: true
				}
			},
			clientViews: {
				files: watchFiles.clientViews,
				options: {
					livereload: true,		
					cwd: 'src/main/resources/assets'
				}				
			},
			clientJS: {
				files: watchFiles.clientJS,
				tasks: ['jshint'],
				options: {
					livereload: true
				}
			},
			clientCSS: {
				files: watchFiles.clientCSS,
				options: {
					livereload: true
				}
			}
		},
		express: {
			options: {
			},
			dev: {
				options: {					
					script: 'server.js'
				}
			}
		},
		jshint: {
			all: {
				src: watchFiles.clientJS.concat(watchFiles.serverJS),
				options: {
					jshintrc: true
				}
			}
		},
		copy: {
			main: {
				expand: true,
				cwd: assets,
				src: [watchFiles.clientViews],
				dest:  dist
			},				
			ViewerJS: {
				expand: true,
				cwd: assets ,
				src: [watchFiles.clientJS],
				dest: dist
			},
			ViewerCSS:{
				expand: true,
				cwd: assets,
				src: [watchFiles.clientCSS],
				dest: dist
			},
			copybower:{
				expand: true,
				src: ['bower_components/**'],
				dest: assets
			}
		},
		clean: [dist],		
		includeSource: {		
			options: {
				basePath:[assets],	
				baseUrl: 'assets/'			
			},			
			myTarget: {
				files: {
					'src/main/resources/assets/index.html': 'src/main/resources/assets/index.tpl.html'
				}
			}
		},
		bowerInstall: {		
			target: {
				cwd: assets,
				src: 'src/main/resources/assets/index.html'
			}
		}			
	});	
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-include-source');
	grunt.loadNpmTasks('grunt-bower-install');
	// grunt.loadNpmTasks('grunt-express');
	grunt.loadNpmTasks('grunt-express-server');
	grunt.registerTask('build',['clean','jshint','includeSource','bowerInstall','copy']);
	grunt.registerTask('default', [ 'express:dev', 'watch' ]);
};