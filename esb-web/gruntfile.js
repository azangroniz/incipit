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
				files:  [ 'server.js' , 'gruntfile.js'],
				tasks:  [ 'express:dev' ],
				options: {
					spawn: false
				}
			},
			serverJS: {
				files: watchFiles.serverJS,
				tasks: ['jshint'],
				options: {
					livereload: true,
					cwd: assets
				}
			},
			clientViews: {
				files: watchFiles.clientViews,
				options: {
					livereload: true,		
					cwd: assets
				}				
			},
			clientJS: {
				files: watchFiles.clientJS,
				tasks: ['jshint'],
				options: {
					livereload: true,
					cwd: assets
				}
			},
			clientCSS: {
				files: watchFiles.clientCSS,
				options: {
					livereload: true,
					cwd: assets
				}
			}
		},
		express: {
			dev: {
				options: {					
					script: 'server.js',
					livereload: true
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
				cwd: assets,
				src: ['bower_components/**'],
				dest: dist
			}
		},
		clean: [dist],		
		includeSource: {		
			options: {
				basePath:[assets , assets +'/'+ watchFiles.clientJS],	
				baseUrl: ''			
			},			
			myTarget: {
				files: {
					'src/main/resources/assets/index.html': 'src/main/resources/assets/index.tpl.html'
				}
			}
		},
		bowerInstall: {		
			target: {
				cwd: '',
				src: 'src/main/resources/assets/index.html'
			}
		},
		'bower-install-simple': {
			clean: 'src/main/resources/assets/lib',
			options: {
				color: true,
				directory: 'src/main/resources/assets/lib'
			},
			'prod': {
				options: {
					production: true
				}
			},
			'dev': {
				options: {
					production: false
				}
			}
		}			
	});	
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-include-source');
	grunt.loadNpmTasks('grunt-bower-install');
	grunt.loadNpmTasks('grunt-bower-install-simple');
	grunt.loadNpmTasks('grunt-express-server');
	grunt.registerTask('reBower',['bower-install-simple:dev']);
	grunt.registerTask('build',['clean','jshint','includeSource','bowerInstall','copy']);
	grunt.registerTask('server', [ 'express:dev', 'watch' ]);
}; 