require "securerandom"

class IdentitiesController < ::ApplicationController
  def create
    render :status => :ok, :json => {
      :token => ::SecureRandom.uuid,
      :uid => "user.#{::Random.new.rand(1000)}"
    }
  end

  def delete
    head :status => :no_content
  end
end
