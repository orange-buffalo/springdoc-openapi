/*
 *
 *
 *  *
 *  *  *
 *  *  *  * Copyright 2019-2020 the original author or authors.
 *  *  *  *
 *  *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *  * you may not use this file except in compliance with the License.
 *  *  *  * You may obtain a copy of the License at
 *  *  *  *
 *  *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *  *
 *  *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *  * See the License for the specific language governing permissions and
 *  *  *  * limitations under the License.
 *  *  *
 *  *
 *
 */

package test.org.springdoc.api.app90.book;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.fn.builders.ParameterBuilder;
import reactor.core.publisher.Flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springdoc.webflux.core.fn.builders.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static test.org.springdoc.api.AbstractSpringDocTest.HANDLER_FUNCTION;

@Configuration
class BookRouter {


	@Bean
	RouterFunction<?> bookRoute(BookRepository br) {
		return route().GET("/books", accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML), HANDLER_FUNCTION, ops -> ops
				.beanClass(BookRepository.class).beanMethod("findAll")).build()

				.and(route().GET("/books", accept(MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN), HANDLER_FUNCTION,
						ops -> ops.tag("book").beanClass(BookRepository.class).beanMethod("findAll")).build())

				.and(route().GET("/books/{author}", HANDLER_FUNCTION, ops -> ops.tag("book")
						.beanClass(BookRepository.class).beanMethod("findByAuthor")
						.operationId("findByAuthor").parameter(ParameterBuilder.builder().in(ParameterIn.PATH).name("author"))).build());
	}

	@Component
	class  BookRepository {

		Flux<Book> findByAuthor(String author){
			return Flux.just(new Book("1", "title1","author1"));
		}

		Flux<Book> findAll() {
			return Flux.just(new Book("2", "title2","author2"));
		}
	}

	public class Book {

		private String id;
		private String title;
		private String author;

		public Book(String id, String title, String author) {
			this.id = id;
			this.title = title;
			this.author = author;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}
	}

}
