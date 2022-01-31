package com.jcg.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class NioChannel {

	public static void main(String[] args) throws IOException {

		RandomAccessFile input = new RandomAccessFile ("sample.txt","r");
		ReadableByteChannel source = input.getChannel();
		RandomAccessFile output = new RandomAccessFile ("output.txt","rw");
		WritableByteChannel destination = output.getChannel();
		copySourceToDest(source, destination);
	}
	

		private static void copySourceToDest(ReadableByteChannel source, WritableByteChannel destination) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(50);
		while (source.read(buffer) != -1) {
			buffer.flip(); //used to flip from read to write so that operation can be performed in parallel threads

			while (buffer.hasRemaining()) {
				destination.write(buffer);
				System.out.println("Writing "+buffer.capacity() +"records");
			}
			System.out.println("Written "+buffer.capacity()  +"records");
			// Now The Buffer Is Empty!
			buffer.clear();
		}
	}
}