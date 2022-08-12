class ApplicationController < ::ActionController::API
  before_action :request_duration
  after_action :echo_headers
  after_action :echo_cookies

  private

  def request_duration
    request_duration = request.headers["HTTP_REQUEST_DURATION"]
    if request_duration.present?
      ::Rails.logger.info("Applying REQUEST_DURATION (#{request_duration})")
      sleep(request_duration.to_i)
    end
  end

  def echo_headers
    echo_headers = request.headers["HTTP_ECHO_HEADERS"]
    if echo_headers.present?
      echo_headers.split(',').each do |header|
        k,v = header.split('=')
        response.headers[k] = v
      end
    end
  end

  def echo_cookies
    echo_cookies = request.headers["HTTP_ECHO_COOKIES"]
    if echo_cookies.present?
      ::Rails.logger.info("Applying ECHO_COOKIES (#{echo_cookies})")
      echo_cookies.split(',').each do |cookie|
        k,v = cookie.split('=')
        ::Rails.logger.info("  Cookie: #{k}=#{v}")
        headers["Set-Cookie"] = "#{k}=#{::URI::escape(v)}; Expires=#{1.minute.from_now.utc.to_s(:rfc822)}; Path=/; HttpOnly"
      end
    end

  end
end
