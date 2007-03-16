class AltN8
    def initialize
        @obj = eval( File.content( File.join( File.dirname(__FILE__), "altn8.alt" ) ) )
    end

		# poss add alternates_for to return more than one possible output.
    def alternate_for(file_name, file_type)

        if (@obj.has_key? file_type)
						results = []

            mappings = @obj[file_type]
            mappings.each do |matcher, replacer|
                result = file_name.gsub(matcher, replacer)
                if result != file_name
                		results << result
                end
            end

						return [file_name] if results.empty?
            return results.uniq
        end

				puts "Unknown Type: #{file_type}"
				return [file_name]
    end
end

class File
    def self.content(file_name)
        return File.readlines(file_name).join
    end
end