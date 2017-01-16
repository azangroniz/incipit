module.exports = function(grunt){
	var assets = 'src/main/resources/assets/';
	var watchFiles = {
		serverViews: ['app/views/**/*.*'], 
		serverJS: ['gruntfile.js', 'server.js', 'config/**/*.js', 'app/**/*.js'],
		clientViews: [assets +'/modules/**/views/*.html'],
		clientJS: [ assets + 'js/*.js', assets +'modules/**/*.js'],
		clientCSS: [assets +'css/*css', assets +'modules/**/*.css']
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
					jshintrc: true
				}
			}
		},
		copy: {
			main: {
				expand: true,
				cwd: assets,
				src: ['modules/**/views/*.html', 'modules/**/img/**'],
				dest:  assets + 'dist'
			},				
			ViewerJS: {
				expand: true,
				cwd: assets,
				src: ['ViewerJS/**'],
				dest: assets + 'dist'
			}
		},
		clean: {
			main: ['public/dist'],
			app: ['public/dist/application.js']
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
	grunt.loadNpmTasks('grunt-express');
	grunt.registerTask('default',['copy']);
	grunt.registerTask('server',['express','watch']);

}