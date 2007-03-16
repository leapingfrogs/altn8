require 'test/unit'
require 'altn8'

class TestAltN8 < Test::Unit::TestCase
	def setup
		@altn8 = AltN8.new
	end

	def test_simple_translation
	   assert_equal ["wibble.txt"], @altn8.alternate_for( "wibble.txt", "PLAIN_TEXT" )
	   assert_equal ["wibble.sp"], @altn8.alternate_for( "test_wibble.rb", "PLAIN_TEXT" )
	   assert_equal ["TestWibble.java"], @altn8.alternate_for( "Wibble.java", "JAVA" )
	end

	def test_multi_output
	   assert_equal ["test_wibble.xml", "test_wibble.rb"], @altn8.alternate_for( "wibble.sp", "PLAIN_TEXT" )
	end
end