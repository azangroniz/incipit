module.exports = function(grunt){
	var assets = 'src/main/resources/assets/';
	var dist = 'src/main/resources/assets/dist';
	var watchFiles = {
		serverViews: ['app/views/**/*.*'], 
		serverJS: ['gruntfile.js'],
		clientViews: ['modules/**/views/*.html','modules/**/img/**'],
		clientJS: [ 'modules/**/*.js', 'js/*.js'],
		clientCSS: ['css/*css', assets +'modules/**/*.css']
	};
	grunt.initConfig({
		pkg:grunt.file.readJSON('package.json'),
		watch: {
			serverViews: {
				files: watchFiles.serverViews,
				options: {
					livereload: true
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
				tasks: ['csslint'],
				options: {
					livereload: true
				}
			}
		},
		jshint: {
			all: {
				src: watchFiles.clientJS.concat(watchFiles.serverJS),
				options: {
					jshintrc: false
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
				cwd: assets + '/app',
				src: [watchFiles.clientJS],
				dest: dist
			},
			ViewerCSS:{
				expand: true,
				cwd: assets + '/app',
				src: [watchFiles.clientCSS],
				dest: dist
			},
			bopybower:{
				expand: true,
				cwd: assets ,
				src: ['bower_components/**'],
				dest: dist
			}
		},
		clean: [dist],		
		includeSource: {
			options: {
				basePath:[ dist + '/css', dist + '/js'],	
				baseUrl: 'dist/js/'			
			},
			myTarget: {
				files: {
					'src/main/resources/assets/dist/index.html': 'src/main/resources/assets/app/index.tpl.html'
				}
			}
		},
		bowerInstall: {			
			target: {
				src: 'src/main/resources/assets/dist/index.html'  ,
				cwd: assets,
			}
		},
		express:{
			all:{
				options:{
					port:9000,
					hostname:'localhost',
					bases:['.'],
					livereload:true
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
	grunt.loadNpmTasks('grunt-express');
	grunt.registerTask('build',['clean','copy','includeSource','bowerInstall']);
	grunt.registerTask('default',['build','express','watch']);

}